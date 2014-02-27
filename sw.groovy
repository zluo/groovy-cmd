#!/usr/bin/env groovy
if (args)
{
 //def address = "http://hopey.netfonds.no/posdump.php?date=20140227&paper=${args[0]}.N&csv_format=txt"
 def address = "http://hopey.netfonds.no/tradedump.php?date=20140227&paper=${args[0]}.N&csv_format=txt"
 download(address)
 }
else
{
println "Hello Stranger"
}

def download(address)
{
    def data = new URL(address).getText()
	println data
}