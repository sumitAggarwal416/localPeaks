# localPeaks

The program analyzes a set of elevation data collected in a text file. The text file is formatted as a series of rows and columns. The first row contains two values that are the number of rows of data followed by the number of columns. All the values in the data file are integer values in the range of 15000 to 99000 inclusive. However, the range of data could be smaller than the absolute range.

The program finds the lowest elevation in the dataset and prints it along with the number of times it occurs in the dataset.

The program also finds the local peaks in the dataset in the specific range of values. Basically, it searches if a certain value occurs in a sub-matrix of a defined size. If another element of same elevation occurs very close to it, both the elevations are discarded. If the elevation is too close to the edges of the sub-matrix, they are discarded too.

Calculates the distance between two closest peaks.

Finds the most common elevation in the dataset.
