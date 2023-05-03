import java.util.Random;

public class MultiArray {
    private int[] dimensions;
    private int[] lowerBounds;
    private int[] upperBounds;
    private double[] data;

    public MultiArray(int[] dimensions, int[] lowerBounds, int[] upperBounds) {
        this.dimensions = dimensions;
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
        int size = getSize(dimensions);
        this.data = new double[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            this.data[i] = rand.nextDouble();
        }
    }

    public double getElement(int[] indices) {
        int index = getIndex(indices);
        return data[index];
    }

    public double getElementByAiliff(int[] indices) {
        int index = getIndexByAiliff(indices);
        return data[index];
    }

    public double[] getVector(int[] indices, int dimension) {
        int[] shape = new int[dimensions.length];
        shape[dimension] = dimensions[dimension];
        int size = getSize(shape);
        double[] vector = new double[size];
        for (int i = lowerBounds[dimension]; i <= upperBounds[dimension]; i++) {
            indices[dimension] = i;
            int index = getIndex(indices);
            for (int j = 0; j < size; j++) {
                vector[j] = data[index + j];
            }
        }
        return vector;
    }

    private int getIndex(int[] indices) {
        int index = 0;
        int factor = 1;
        for (int i = dimensions.length - 1; i >= 0; i--) {
            index += (indices[i] - lowerBounds[i]) * factor;
            factor *= dimensions[i];
        }
        return index;
    }

    private int getIndexByAiliff(int[] indices) {
        int index = indices[0] - lowerBounds[0];
        for (int i = 1; i < dimensions.length; i++) {
            index = index * (upperBounds[i - 1] - lowerBounds[i - 1] + 1) + (indices[i] - lowerBounds[i]);
        }
        return index;
    }

    private int getSize(int[] dimensions) {
        int size = 1;
        for (int dim : dimensions) {
            size *= dim;
        }
        return size;
    }

    public static void main(String[] args) {
        int[] dimensions = {100, 100};
        int[] lowerBounds = {0, 0};
        int[] upperBounds = {99, 99};
        MultiArray multiArray = new MultiArray(dimensions, lowerBounds, upperBounds);

        int[] indices = {50, 50};
        long start = System.nanoTime();
        double element = multiArray.getElement(indices);
        long end = System.nanoTime();
        System.out.println("Element access time: " + (end - start) + " ns");

        start = System.nanoTime();
        element = multiArray.getElementByAiliff(indices);
        end = System.nanoTime();
        System.out.println("Ailiff access time: " + (end - start) + " ns");

        start = System.nanoTime();
        double[] vector = multiArray.getVector(indices, 1);
        end = System.nanoTime();
        System.out.println("Vector access time: " + (end - start) + " ns");
    }
}