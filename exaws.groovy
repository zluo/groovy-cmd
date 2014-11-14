/**
 * extract logs
 * @author zluo
 *
 */
class ExtractAwsLog {

  
  
  
  Map<Map<String,List>,Map<String,List>> data= ['46': [:], '48': [:]]
  List<String> outList=[
    "API NAME, 46Total(ms), 46Counts, 46Average, 48Total(ms), 48Counts, 48Average,"
  ]
  Set<String> keyset=[]
  void extract() {
    extract('46')
    extract('48')
    println data
    generateResult()
    outList.each{println it}
    new File("c:/temp/result46vs48.csv").withWriter { out ->
      outList.each { out.println it }
      }
  }

  void extract(String file) {
    println "=========================extract ${file} soap========================"
    Map m = data.get(file)

    new File ("c:/TEMP/soap${file}.txt").eachLine{
      def arr = it.split(" ")
      println "${arr[5]},${arr[6]}"

      def key = arr[5]
      def value =arr[6]


      if (m.get(key)==null){
        m.put(key,[])
      }

      m.get(key).add(value)
      keyset.add(key)
    }
   
    //		writeToConsole()
    //		writeToFile()

  }

  public void writeToFile() {
    new File("c:/temp/result.csv").withWriter { out ->
      outList.each { out.println it }
    }
  }
  public void writeToConsole() {
    outList.each { println it }
  }

  def generateResult(){
    Map map46=data.get('46')
    Map map48=data.get('48')

    keyset.each{
      key ->
      List list46 = map46.get(key)
      List list48 = map48.get(key)

      double sum46=list46?.sum{Integer.valueOf(it)}
      double ave46=sum46/list46.size()
      double sum48=list48?.sum{Integer.valueOf(it)}
      double ave48=sum48/list48.size()

      outList.add("${key},${sum46},${list46.size()},${ave46.round(2)},${sum48},${list48.size()},${ave48.round(2)}")
    }
  }
}
public void writeToFile() {
 
}
// Run the script
new ExtractAwsLog().extract()

