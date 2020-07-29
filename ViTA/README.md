# ViTA
Targeted genome editing has enormous potentia for treating monogenic disorders, especially for those that affect the hematopoietic system. Reducing the immunogenicity of gene-editing components such as CRISPR-Cas9 is critical for ensuring safe treatment. 

Gene corrections may also result in novel epitopes of human leukocyte antigen (HLA). 

ViTA, **V**ar**i**ants **T**o **A**ntigen, is a java application that predicts immunogenicity of HLA-epitopes. 
It supports common input formats: Variant call format (VCF) and Single-variant.
  
## Background
CRISPR/Cas9 and other gene-editing technologies have exciting therapeutic potential. Clinical applications include curing monogenetic disorders or humanizing animal organs for xenotransplantation[1]. Considerable effort and progress has been made to reduce the immunogenicity of microbial-derived core editing components and delivery vectors[2]. 

However, relatively little attention is given to the possibility that somatic gene edits may lead to immunoreactive antigens introduced ex-post facto (Figure: ViTA Algorithmic Flow Chart). 

The effects could be counterproductive if immunocompetent patients reject the therapy or life threatening in cases where human leukocyte antigen (HLA)-bound peptides elicit an alloreactive-like response[3]. 

Here, we present a strategy paired with an efficient application, variant-to-antigen (ViTA), to simulate gene-edited peptide presentation. 

## Algorithmic Flow Chart

![AlgorithmicFlowChart](https://github.com/wwang-nmdp/ViTA/blob/master/doc/image/vitaflow.png)

## Pre-installation

####Install `SnpEff`[4] from http://snpeff.sourceforge.net/index.html  SnpEff is a variant annotation and effect prediction tool. It annotates and predicts the effects of variants on genes (such as amino acid changes).

```unix
wget http://sourceforge.net/projects/snpeff/files/snpEff_latest_core.zip
unzip snpEff_latest_core.zip
java -jar snpEff.jar
```
Run 'java -jar snpEff.jar command' for help on each specific command

Usage: snpEff build [options] genome_version
java -jar snpEff.jar build GRCh38.82
Install `netChop3.1`[5] from Center For Biological Sequence Analysis, Technical University of Denmark.
   The NetChop 3.1 may be downloaded only by special agreement.  For academic users there is a download site at:http://www.cbs.dtu.dk/cgi-bin/nph-sw_request?netchop. Other users are requested to contact   software@cbs.dtu.dk.

```unix
cat netchop-3.1.<unix>.tar.Z | uncompress | tar xvf -
export NETCHOP=/full/path/to/netchop-3.1
export TMPDIR=/full/path/to/netchop-3.1/tmp
```

In the 'netchop-3.1' directory test the software:
bin/netchop test/test.fsa > test.out
  For research purpose, the net Chop3.1 is pre-built in MiHA identification pipeline. Make sure two environment variables, NETCHOPandTMDIR are set properly.Alsomakesurethat TMPDIR has the sticky bit set (the long listing should render 'drwxrwxrwt...'). If not, set it:

```unix
chmod 1777 $TMPDIR
```
#### Install `netMHCpan-3.0`[6] from Center For Biological Sequence Analysis, Technical University of Denmark.
Like netChop, the netMHCpan-3.0 may only be downloaded only by special agreement as well. In addition, it requests to download the data file (data.tar.gz) separately.

```unix
cat netMHCpan-3.0.<unix>.tar.gz | uncompress | tar xvf -

cd ./netMHCpan-3.0
wget http://www.cbs.dtu.dk/services/NetMHCpan-3.0/data.tar.gz
tar -xvf data.tar.gz
```




##### Set environment variables

```unix
export TMPDIR=/Path/to/Tools/netMHCpan-3.0/tmp
export NMHOME=/Path/to/Tools/netMHCpan-3.0/bin
export NETMHCpan=/Path/to/Tools/netMHCpan-3.0
```


##### Build database of Transcriptome


cds.db is pre-built hg38 transcriptome and minor allele frequence (version:snp147common) database, please download by using the provided link: https://sourceforge.net/projects/mihaip/files/cds.db.zip/download

unzip the file:
```unix
unzip cds.db.zip
```
Copy the database into your working directory.


# References:
[1]   Meier, R.P., et al. Xenotransplantation: back to the future?. Transplant International, 2018.31(5): p.465-477.

[2]   Chew WL., et al., Immunity to CRISPR Cas9 and Cas12a therapeutics. Wiley Interdisciplinary Reviews: Systems Biology and Medicine. 2018 Jan;10(1): p. 1408.

[3]   Horowitz, M.M., et al., Graft-versus-leukemia reactions after bone marrow transplantation. Blood, 1990. 75(3): p. 555-62.

[4]	Cingolani, P., et al., A program for annotating and predicting the effects of single nucleotide polymorphisms, SnpEff: SNPs in the genome of Drosophila melanogaster strain w1118; iso-2; iso-3. Fly (Austin), 2012. 6(2): p. 80-92.

[5]	Kesmir, C., et al., Prediction of proteasome cleavage motifs by neural networks. Protein Eng, 2002. 15(4): p. 287-96.

[6]	Nielsen, M. and M. Andreatta, NetMHCpan-3.0; improved prediction of binding to MHC class I molecules integrating information from multiple receptor and peptide length datasets. Genome Med, 2016. 8(1): p. 33.
