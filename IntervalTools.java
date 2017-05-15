import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class IntervalTools {
		
	/**
	 * Return list of {@link Interval} without overlap
	 * @param intervals
	 * @return list of {@link Interval}
	 */
	public List<Interval> mergeIntervals(List<Interval> intervals) {

        if(intervals.size() == 0)
            return intervals;
        if(intervals.size() == 1)
            return intervals;
        
		List<Interval> intervalsSorted = new ArrayList<Interval>();
        
		// Intervals are sorted by startDate the endDate
		intervalsSorted = intervals.stream()
			.sorted(Comparator.comparing(Interval::getStart)
			.thenComparing(Interval::getEnd))
			.collect(Collectors.toList());
        
		Interval first = intervalsSorted.get(0);
        DateTime start = first.getStart();
        DateTime end = first.getEnd();
        
        ArrayList<Interval> result = new ArrayList<Interval>();
        Interval current = null;
        
        for(int i = 1; i < intervalsSorted.size(); i++){
            current = intervalsSorted.get(i);
            if(current.getStart().isBefore(end) || current.getStart().equals(end)){
                end = current.getEnd().isAfter(end) ? current.getEnd() : end;
            }else{
            	first.withStart(start);
            	first.withEnd(end);
                result.add(first);
                start = current.getStart();
                end = current.getEnd();
                first = current;
            }  
        }
        
        current.withStart(start);
        current.withEnd(end);
        result.add(current);
        
        return result; 
    }
	
}
