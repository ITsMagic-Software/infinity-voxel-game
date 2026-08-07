package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/**
 * @Author 
*/
public class GenData { 

    public int dirtLayerHeight = 4;
    public int beachLevel = 34;
    public int beachDepth = 3;
    public int caveDepthFromSurface = 8;
    public float caveThreshold = 0.62f;
    public int seed = 0;

    private PerlinNoise continentalNoise = new PerlinNoise();
    private PerlinNoise hillNoise = new PerlinNoise();
    private PerlinNoise detailNoise = new PerlinNoise();
    private PerlinNoise mountainNoise = new PerlinNoise();
    private PerlinNoise moistureNoise = new PerlinNoise();
    private PerlinNoise temperatureNoise = new PerlinNoise();
    private PerlinNoise forestNoise = new PerlinNoise();
    private PerlinNoise caveNoise = new PerlinNoise();
    private BiomeGenerator biomeGenerator = new BiomeGenerator();

    public void start() {
        biomeGenerator.start();
    }

    public int getGroundHeight(int x, int z, int minimalGroundHeight, int maxHillHeight, int baseHillWidth) {
        continentalNoise.setScale(baseHillWidth * 4);
        hillNoise.setScale(baseHillWidth);
        detailNoise.setScale(java.lang.Math.max(6, baseHillWidth / 3));
        mountainNoise.setScale(baseHillWidth * 2);

        float continental = normalize(continentalNoise.noise(x + seedOffset(101), z - seedOffset(101)));
        float hills = normalize(hillNoise.noise(x + seedOffset(203), z - seedOffset(307)));
        float detail = normalize(detailNoise.noise(x - seedOffset(409), z + seedOffset(503)));
        float mountains = normalize(mountainNoise.noise(x + seedOffset(607), z + seedOffset(701)));

        mountains = java.lang.Math.max(0f, (mountains - 0.58f) / 0.42f);
        mountains *= mountains;

        float height = minimalGroundHeight;
        height += (continental - 0.5f) * maxHillHeight * 0.9f;
        height += hills * maxHillHeight * 0.75f;
        height += detail * 4f;
        height += mountains * maxHillHeight * 1.35f;

        return java.lang.Math.max(1, (int) height);
    }

    public int getBiome(int x, int z, int groundHeight) {
        moistureNoise.setScale(90);
        temperatureNoise.setScale(120);

        float moisture = normalize(moistureNoise.noise(x - seedOffset(809), z + seedOffset(907)));
        float temperature = normalize(temperatureNoise.noise(x + seedOffset(1009), z - seedOffset(1103)));
        return biomeGenerator.getBiome(moisture, temperature, groundHeight, beachLevel);
    }

    public boolean isBeach(int groundHeight) {
        return groundHeight <= beachLevel;
    }

    public boolean shouldPlaceTree(int x, int z, int groundHeight) {
        int biome = getBiome(x, z, groundHeight);
        if (biome == BiomeGenerator.BEACH || biome == BiomeGenerator.DESERT || biome == BiomeGenerator.MOUNTAINS) {
            return false;
        }

        forestNoise.setScale(18);
        float density = normalize(forestNoise.noise(x + seedOffset(1201), z - seedOffset(1301)));
        float limit = biome == BiomeGenerator.FOREST ? 0.64f : 0.82f;
        return density > limit;
    }

    public int getTreeHeight(int x, int z, int minHeight, int maxHeight) {
        int range = java.lang.Math.max(1, maxHeight - minHeight + 1);
        return minHeight + positiveHash(x, z, 17) % range;
    }

    public int getTreeRadius(int x, int z, int baseRadius) {
        return java.lang.Math.max(2, baseRadius + (positiveHash(x, z, 41) % 2));
    }

    public boolean shouldCarveCave(int x, int y, int z, int groundHeight) {
        if (y >= groundHeight - caveDepthFromSurface || y < 4) {
            return false;
        }

        caveNoise.setScale(24);
        float horizontal = normalize(caveNoise.noise(x + seedOffset(1409), z - seedOffset(1511)));
        caveNoise.setScale(14);
        float vertical = normalize(caveNoise.noise(x + y * 3 + seedOffset(1601), z - y * 2 - seedOffset(1709)));
        return horizontal * 0.65f + vertical * 0.35f > caveThreshold;
    }

    public int positiveHash(int x, int z, int salt) {
        int h = x * 73428767 ^ z * 912931 ^ salt * 42349 ^ seed * 374761393;
        h ^= h >> 13;
        h *= 1274126177;
        h ^= h >> 16;
        return h & 0x7fffffff;
    }

    private float normalize(float v) {
        return (v + 1f) / 2f;
    }

    private int seedOffset(int salt) {
        int h = seed * 374761393 ^ salt * 668265263;
        h ^= h >> 13;
        h *= 1274126177;
        h ^= h >> 16;
        return 10000 + (h & 0x7fffffff) % 90000;
    }
}
