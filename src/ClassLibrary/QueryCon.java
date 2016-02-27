package ClassLibrary;

import java.io.File;

public class QueryCon{
    // private class fields
    private final String conFile;
    private final boolean valid;
    
    public QueryCon(String conFile, String keyword, int numAppear){
      File concordFile = new File(conFile);
      if (concordFile.isFile()){
          this.conFile = concordFile.getName();
          IO<Concord> io = new IO<Concord>(conFile);
          Concord concordance = (Concord)io.deserialize(conFile);
          this.valid = concordance.get_number_occurrences(keyword) >= numAppear;
      }
      else{
          this.conFile = null;
          this.valid = false;
      }
    }
    
    public boolean getValid(){
        return this.valid;
    }
    
    public String getConFile(){
        return this.conFile;
    }
}