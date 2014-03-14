#!/usr/bin/env groovy

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper

def dataFile=".dirlist"

def result=[:]

def curDir=new File(".").getCanonicalPath()

def slurper = new JsonSlurper()

def cmd_home=System.getenv().get("GROOVY_CMD_HOME");
if (cmd_home !=null)
{
  dataFile=cmd_home + "/" + dataFile
}
try {
	result= slurper.parseText(new File(dataFile).getText())
}
catch(Throwable e)
{
	println ".dirlist is not existing"
}

def value=result.get(curDir);
if (value ==null) value=0
result.put(curDir, ++value)



def builder =new  JsonBuilder(result)
new File(dataFile).write(builder.toString())   
println "Push [$curDir] to $dataFile"
