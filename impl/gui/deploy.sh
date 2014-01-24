#!/bin/bash
echo 'CRONOS'
mvn package

#keytool -genkey -keystore cronosKeys -alias jdc
#jarsigner -keystore cronosKeys target/cronos-1.0.0-standalone.jar jdc

echo 'SCP'
scp target/cronos-1.0.0-standalone.jar vanderson@hercules.ppgia.pucpr.br:/home2/doutorado/2013/vanderson/public_html/cronos/cronos.jar
