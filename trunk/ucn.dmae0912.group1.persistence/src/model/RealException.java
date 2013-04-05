package model;

public class RealException extends Exception
{
	
  public static final long serialVersionUID = 1L;
  String mistake;
  public RealException()
  {
    super();
    mistake = "Unknown error";
  }
  
  public RealException(String err)
  {
    super(err);
    mistake = err;
  }
  
  
  
  public String getError()
  {
    return mistake;
  }
}
