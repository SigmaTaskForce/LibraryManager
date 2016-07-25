#!/bin/bash
cd ./src
java -cp ../lib/mysql-connector-java-5.1.35-bin.jar:../lib/itextpdf-5.5.9.jar:../lib/itext-pdfa-5.5.9.jar:../lib/itext-xtra-5.5.9.jar:. libman.MainUI
