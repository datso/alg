import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class LngPok {

    private static String DEFAULT_FILE = "/Users/vruban/Projects/alg/lngpok/examples/15.in";

    public static void main(String[] args) throws Exception {
        Instant starts = Instant.now();
        String file = DEFAULT_FILE;

        if (args.length >= 2) {
            file = args[0];
        } else if (args.length > 1) {
            file = args[0];
        }

        // Step 1: Read input data
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));
        int[] cards = parseCards(br.readLine());

        // Step 2: Sort our cards for further processing
        Arrays.sort(cards);

        // Step 2.a: Calculate jokers
        int jokers;
        for (jokers = 0; jokers < cards.length; jokers++) {
            if (cards[jokers] != 0) {
                break;
            }
        }

        // Step 3: Make new `Segments` array
        List<Segment> segments = new ArrayList<>();
        Segment lastSegment = null;

        int length = 0;
        int start = cards[jokers];

        for (int i = jokers; i <= cards.length; i++) {
            if (i != cards.length) {
                if (i != 0 && cards[i] == cards[i-1]) {
                    continue; // Drop non-unique cards
                } else if (i == jokers || cards[i] == cards[i-1] + 1) {
                    length++;
                    continue;
                }
            }

            int pad = lastSegment != null ? start - (lastSegment.start + lastSegment.length) : 0;

            Segment segment = new Segment(start, length, pad);

            segments.add(segment);
            lastSegment = segment;

            if (i != cards.length) {
                start = cards[i];
                length = 1;
            }
        }

        // Step 5: Iterate for each segment and define maximum length of sequence O(K * N * N)
        int max = 0;

        if (jokers == 0 || segments.size() < 2) {
            max = Collections.max(segments, Comparator.comparingInt(a -> a.length)).length + jokers;
        } else {
            for (int i = 1; i < segments.size(); i++) {
                Segment segment = segments.get(i);
                int current = segment.length;
                int remains = jokers;

                if (remains >= segment.pad) {
                    current += segment.pad;
                    remains -= segment.pad;

                    for (int p = i - 1; p >= 0; p--) {
                        Segment previous = segments.get(p);
                        current += previous.length;

                        if (remains >= previous.pad) {
                            current += previous.pad;
                            remains -= previous.pad;
                        } else {
                            break;
                        }
                    }
                }

                current += remains;
                max = current > max ? current : max;
            }
        }

        Instant ends = Instant.now();
        System.out.println("Result: " + max + " (" + Duration.between(starts, ends) + ")");
    }

    private static int[] parseCards(String s) {
        String[] items = s.split(" ");
        int[] ints = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            ints[i] = Integer.parseInt(items[i]);
        }

        return ints;
    }

    private static class Segment {
        int start = 0;
        int length = 0;
        int pad = 0; // Length to previous Segment (e.g. how many jokers we need to connect to)

        public Segment(int start, int length, int pad) {
            this.start = start;
            this.length = length;
            this.pad = pad;
        }
    }

}
