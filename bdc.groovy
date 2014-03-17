#!/usr/bin/env groovy

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper

def dataFile=".vocabularylist"
def dicFile ="ew.txt"

def result=[:]



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
	println ".vocabularylist is not existing"
	new File(dicFile).getText().eachLine {result.put(it, new Record(0,0))}
}


def builder =new  JsonBuilder(result)
new File(dataFile).write(builder.toString())   

class Record
{
	int times;
	int score;
	
	
}