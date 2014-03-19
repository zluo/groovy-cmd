#!/usr/bin/env groovy
/**
 * memorize the input parameters
 * -----------------------------------------------------------------------
 *"UnKnown arguments, Usage: memorize [.dataFile] [-command] [entry]  
 *by convention, dataFile start with [.]
 *memorize .data    			  List memorize history
 *memorize -r [number]|[entry]    remove a entry
 *memorize -p [entry]   		  push a entry
 */

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper

new MemorizeImpl().run(args)

class MemorizeImpl {
	Map result=[:]
	def curEntry=null
	def dataFile=".diclist"
	def command='p'
	def argIndex = -1

	def run(args) {
		parse(args)
		loadData()
		processCommand(command, argIndex)
		if (curEntry !=null) new File(".entry").write("$curEntry")
	}

	/**
	 * List Command
	 */
	void l() {
		result.eachWithIndex {key, value, index -> println "$index. $key, $value"}
		System.exit(1)
	}

	/**
	 * Push Command
	 */
	void p() {
		def value=result.get(curEntry);
		if (value ==null) value=0
		result.put(curEntry, ++value)
		writeData()
	}

	/**
	 * Change Directory Command
	 * @param index
	 */
	void p(index) {
		curEntry= result.keySet()[index]
		p()
	}

	/**
	 * Remvoe Directory from List Command
	 * @param index
	 */
	void r(index) {
		curEntry=result.keySet()[index]
		r()
	}
	void r() {
		result.remove(curEntry)
		writeData()
		println "Remove [$curEntry] from $dataFile"
		System.exit(1)
	}

	/**
	 * Load directory list from file
	 * @return
	 */
	def loadData() {
		try {
			def slurper = new JsonSlurper()
			def cmd_home=System.getenv().get("GROOVY_CMD_HOME");
			if (cmd_home !=null) {
				dataFile=cmd_home + "/" + dataFile
			}
			result=slurper.parseText(new File(dataFile).getText()).sort{-it.value}
			if (argIndex>result.size()) {
				println "[Warning] Index number $argIndex is out of range of Directory list"
				argIndex=-1
			}
		}
		catch(Throwable e) {
			println e
		}
	}

	/**
	 * Save directory to file
	 * @return
	 */
	def writeData() {
		def builder =new  JsonBuilder(result)
		new File(dataFile).write(builder.toString())
	}

	/**
	 * Parse Command Line
	 * @param args
	 * @return
	 */
	def parse(String[] args) {
		args.each{ it ->
			if (it.startsWith('-')){
				command=it.substring(1)
			}
			else if (it.startsWith('.')){
				dataFile=it
			}
			else {
				try {
					argIndex=Integer.valueOf(it)
				}
				catch (Exception e){
					curEntry=it
				}
			}
		}
		
		if (curEntry==null && argIndex==-1) command='l'
	}

	/**
	 * Dispatch Command
	 * 
	 * @param command
	 * @param argIndex
	 * @return
	 */
	def processCommand(String command, int argIndex) {
		if (argIndex==-1){
				"$command"()
		}
		else {
			"$command"(argIndex)
		}
	}
	def methodMissing(String name, args) {
		println """UnKnown arguments, Usage: memorize [.dataFile] [-command] [entry]  
*by convention, dataFile start with [.]
memorize .data    			  List memorize history
memorize -r [number]|[entry]  remove a entry
memorize -p [entry]   		  push a entry
ld [nubmer]       Change directory to entry of [number]
     """
	}
}


