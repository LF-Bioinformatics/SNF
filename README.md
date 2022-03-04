# SNF

SNF Version 1.3.1

SNF is a program that can be used for the identification of small RNAs produced from inverted repeat loci. 

System requirements: Linux OS, Java and (optional) an internet connection for automated installation of dependencies – see below.

Installation:
Download and unzip the program folder and install dependencies below.

Dependencies:
The following software is required:

Conda (https://docs.conda.io/en/latest/miniconda.html). 

Conda packages: grf (https://anaconda.org/bioconda/genericrepeatfinder), fastqc (https://anaconda.org/bioconda/fastqc), seqkit (https://anaconda.org/bioconda/seqkit), blast (https://anaconda.org/bioconda/blast), PaTMaN [1] (https://bioinf.eva.mpg.de/patman/).

Please see installation instructions for how to install conda and the required conda packages (https://docs.conda.io/en/latest/miniconda.html#linux-installers) 

For convenience, it is possible to automate the download and installation of Conda, Conda packages and Patman by setting the following fields in the “properties.config” file to true (default = false):
INSTALL_CONDA=true
INSTALL_CONDA_TOOLS=true
INSTALL_PATMAN=true


Usage: asnf [-d hd] [-a adapter] [-r ratio] -I runID -F fastq(s) -R reference.fasta -A annotation.fasta

-I:   Mandatory - A unique identifier for this analysis/run.

-F:  Mandatory - Small RNAs in fastq format,

-R:  Mandatory - Reference genome in fasta format.

-A:  Mandatory - Annotation file containing genes in fasta format.

-d:  Optional - HD adapters used? true if sRNAs were obtained using High Definition Adapters, otherwise false.

-a:  Optional - Adapter sequence to be trimmed from small RNA fastq(s). If no adapter is supplied, Trimalore! auto detect adapter will be used.

-r:  Optional - Maximum length ratio of spacer/total sequence used by GRF.

-h:  Help - Help page.
        
This software is an open-source tool and is released under the 3-clause BSD license.
