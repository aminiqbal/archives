# K-Means Engine

Performs basic K-Means algorithm on simple datasets of the form:
```
data-label-1,data-label-2
111.111,222.222
33.33,44.44
11.1111,2.2
333,444.4
...
```

## Features

* Supports ```k``` up to 9.
* Supports basic plot as well as color coded clusters plot.
* Supports double-precision.

## Details

Intended for Java18: .jar is based on and requires Java18.
Re-compile for use with other Java versions.

## Usage

```
java -jar kmeans.jar "full/path/to/dataset.ext"
```
