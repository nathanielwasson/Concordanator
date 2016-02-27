package ClassLibrary;
 
import java.io.File;

public class QueryCon implements Runnable {
    // private class fields
    private String conFile;
    private final int numAppear;
    private final String keyword;
    
    public QueryCon(String cf, String kd, int num){
      this.conFile = cf;
      this.keyword = kd;
      this.numAppear = num;
    }

    @Override
    public void run() {
        File concordFile = new File(conFile);
      if (concordFile.isFile()){
          IO<Concord> io = new IO<Concord>(conFile);
          Concord concordance = (Concord)io.deserialize(conFile);
          if(concordance.get_number_occurrences(keyword) >= numAppear){
              System.out.println(concordFile.getName());
          } 
      }
    }
}