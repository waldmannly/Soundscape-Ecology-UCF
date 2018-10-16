// StringLengthComparator.java
import java.util.Comparator;

public class AssignmentComparator implements Comparator<Assignment>
{
    @Override
    public int compare(Assignment x, Assignment y)
    {
    	int xp = x.getPriority();
    	int yp = y.getPriority(); 
    	return yp-xp;
    	
    	/*
        if (xp > yp)
        {
            return -1;
        }
        if (xp < yp)
        {
            return 1;
        }
        return 0;
        */
    }
}