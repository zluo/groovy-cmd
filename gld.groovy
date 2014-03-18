#!/usr/bin/env groovy
/**
 * Push/List/Remove/Change Directory
 * -----------------------------------------------------------------------
 * ld.cmd          List Directory History, without parameter, list Directory History
 *				with number, goto selected directory
 *				
 *				ld -l or ld    list directory
 *				ld -p          push current directory
 *				ld -r 0        remove directory 0 from the list
 *				ld 0           cd directory 0
 */

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper

new DirtoryImpl().run(args)

class DirtoryImpl {
	Map result=[:]
	def curDir=new File(".").getCanonicalPath()
	def dataFile=".dirlist"
	def command="l"
	def argIndex = -1
	
	def run(args) {
		loadData()
		parse(args)
		processCommand(command, argIndex)
		def f= new File(".ld.cmd")
		f.write("cd $curDir")
	}

    /**
     * List Command
     */
	void l() {
		result.eachWithIndex {key, value, index -> println "$index. $key, $value"}
	}
    
	/**
	 * Push Command
	 */
	void p() {
		def value=result.get(curDir);
		if (value ==null) value=0
		result.put(curDir, ++value)
		writeData()
	}

	/**
	 * Change Directory Command
	 * @param index
	 */
	void l(index) {
		curDir= result.keySet()[index]
		p()
	}

	/**
	 * Remvoe Directory from List Command
	 * @param index
	 */
	void r(index) {
			def key=result.keySet()[index]
			result.remove(key)
			writeData()
			println "Remove [$curDir] from $dataFile"
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
		args.each{it ->
			if (it.startsWith('-')){
				command=it.substring(1)
			}
			else {
				argIndex=Integer.valueOf(it)
				if (argIndex>result.size()) {
					println "[Warning] Index number $argIndex is out of range of Directory list"
					argIndex=-1
				}
			}
		}
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
      println """UnKnown arguments, Usage: 
ld                list directory
ld -p             push current directory
ld -r [number]    Remove directory entry of [number] from the list
ld [nubmer]       Change directory to entry of [number]
     """
	}
}


