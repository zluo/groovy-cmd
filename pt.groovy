/**
 * PinYing Test Program
 */

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper

new PinYinTest().run();

class PinYinTest{

  def shuangPinMap =["a":"a", "ai":"l", "ao":"k" ,"an":"j","ang":"h","er":"r","ei":"z","en":"f","eng":"g","ia":"w","ie":"x",
    "i":"i","iu":"q","in":"n","ing":";","ian":"m","iao":"c","iang":"d","o":"o","ou":"b","ong":"s","ua":"w","ui":"v","uo":"o","un":"p","uan":"r",
    "uai":"y","uang":"d","sh":"u","ch":"i","zh":"v"];

  Map errorMap=[:]
  Map result =[:]
  int score=0;
  int total=0;
  String dataFile=".pinyin_test"

  def examClosure = {k, v ->

    def answer = System.console().readLine "[${k}] : "
    if (answer=="0") {
      println "exiting..."
      return true;
    }
    if (answer==shuangPinMap.getAt(k)) {
      if (result.get(k)==null) {
        result.put(k,1)
      }
      else {
        result.put(k,result.get(k)+1)
      }
      score ++;
    }else{
      errorMap.put(k,shuangPinMap.getAt(k))
      if (result.get(k)==null) {
        result.put(k,-2)
      }
      else {
        result.put(k,result.get(k)-2)
      }
      println "${shuangPinMap.getAt(k)}"
      errorMap.find(examClosure)
    }
    return false
  }

  def run() {
    loadData()
    def start = System.currentTimeMillis()

    result.find (examClosure)

    shuangPinMap.find (examClosure)
    def time = (System.currentTimeMillis()-start)/1000.0
    println "score [${score}/${shuangPinMap.size()}],   Time[${time}]ms"
    writeData()
  }

  /** Load directory list from file
   * @return
   */
  def loadData() {
    try {
      def slurper = new JsonSlurper()
      def cmd_home=System.getenv().get("GROOVY_CMD_HOME");
      if (cmd_home !=null) {
        dataFile=cmd_home + "/" + dataFile
      }
      result=slurper.parseText(new File(dataFile).getText()).sort{it.value}
      println result
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
}
