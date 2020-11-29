import java.util.Comparator;
public class myComp implements Comparator<obj>
{ 
public int compare(obj x,obj y)
{ if(x.val<y.val)
    return 1;
    
  if(x.val>y.val)
    return -1;
    
    else return 0;
} 
}