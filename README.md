# SNF

SNF Version 1.4.1

SNF is a program that can be used for the identification of small RNAs produced from inverted repeat loci. 

System requirements: Linux OS, Java and (optional) an internet connection for automated installation of dependencies – see below.

Installation:
Download and unzip the program folder and install dependencies below.

Dependencies:
The following software is required:

Conda (https://docs.conda.io/en/latest/miniconda.html). 

Conda packages: grf (https://anaconda.org/bioconda/genericrepeatfinder), fastqc (https://anaconda.org/bioconda/fastqc), seqkit (https://anaconda.org/bioconda/seqkit), blast (https://anaconda.org/bioconda/blast), PaTMaN [1] (https://bioinf.eva.mpg.de/patman/).

Please see installation instructions for how to install conda and the required conda packages (https://docs.conda.io/en/latest/miniconda.html#linux-installers) 

For convenience, it is possible to automate the download and installation of Conda, Conda packages and Patman. Set the following fields in the “properties.config” file to true (default = false): INSTALL_CONDA=true, INSTALL_CONDA_TOOLS=true, INSTALL_PATMAN=true. Then run the tool. Using this method will automatically update the user's .bashrc with the conda install and update the tool's parameters.config with the patman location.


Usage: asnf [-d hd] [-a adapter] -O output_directory -F fastq(s) -R reference.fasta -A annotation.fasta -C configuration_file

-C:  Mandatory - Configuration file containing default parameters and user defined paths (see configuration note below).

-O:  Mandatory - Output directory for this analysis/run.

-F:  Mandatory - Small RNAs in fastq format,

-R:  Mandatory - Reference genome in fasta format.

-A:  Mandatory - Annotation file containing genes in fasta format.

-d:  Optional - HD adapters used? true if sRNAs were obtained using High Definition Adapters, otherwise false.

-a:  Optional - Adapter sequence to be trimmed from small RNA fastq(s). If no adapter is supplied, Trimalore! auto detect adapter will be used.

-h:  Help - Help page.

CONFIGURATION NOTE
Please ensure that the INSTALL_DIR variable inside the configuration file is set to the software installation directory.

This software is an open-source and is released under the 3-clause BSD license.
