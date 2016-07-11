# JProperties
This project enhances the initial java.util.Properties class by giving tools and methodology to easily use properties file inside your project.

#QuickTour
Imagine you have the following myProps.properties file to handle:

    working.dir=/path/to/my/working/dir
    ip.rest.server=10.15.18.19
  
  In order to use those properties you have to create an enum wich implements com.triaubaral.util.Property interface.
  For example : 
  
    public enum MyProps implements com.triaubaral.util.Property{
    
      WORKING_DIR("working.dir"),
      IP_REST_SERVER("ip.rest.server");
  
      private String code;
    
      public MyProps(String code){
        this.code = code;
      }
    
      public String getCode(){
        return this.code;
      }
    
      public String getClasspath(){
        return "/path/in/classpath/MyProps.properties";
      }
  
    }
  
  Then you can use the preceeding properties in your code by using the following snipset :
  
      String valueOfMyProperties = PropertiesReader.INSTANCE.readQuietly(Property.WORDING_DIR);
      System.out.println("working.dir value : "+valueOfMyProperties);
      //working.dir value :  /path/to/my/working/dir
      
  
  
  
