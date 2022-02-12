package services;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import haagahelia.sp.app.domain.Entry;

@Service
public class EntryService {
	
	public static class sortByDate implements Comparator<Entry> {
        @Override
        public int compare(Entry a, Entry b) {
            return a.getDate().compareTo(b.getDate());
        }
    }
}
