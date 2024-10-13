import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class optimal {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int frames, pointer = 0, hit = 0, fault = 0, ref_len;
        boolean isFull = false;
        int buffer[];
        int reference[];
        int mem_layout[][];

        System.out.println("Please enter the number of Frames: ");
        frames = Integer.parseInt(br.readLine());
        System.out.println("Please enter the length of the Reference string: ");
        ref_len = Integer.parseInt(br.readLine());
        
        reference = new int[ref_len];
        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];
        
        // Initialize the buffer with -1 indicating empty frame slots
        for (int j = 0; j < frames; j++) {
            buffer[j] = -1;
        }

        System.out.println("Please enter the reference string: ");
        for (int i = 0; i < ref_len; i++) {
            reference[i] = Integer.parseInt(br.readLine());
        }
        
        System.out.println();
        
        // Process each reference string entry
        for (int i = 0; i < ref_len; i++) {
            int search = -1;

            // Check if the current page is already in one of the frames
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j; // Page found
                    hit++;
                    break;
                }
            }

            // If the page is not found, it's a page fault
            if (search == -1) {
                if (isFull) {
                    int index[] = new int[frames];
                    boolean index_flag[] = new boolean[frames];

                    // Find the furthest reference in future
                    for (int j = i + 1; j < ref_len; j++) {
                        for (int k = 0; k < frames; k++) {
                            if (reference[j] == buffer[k] && !index_flag[k]) {
                                index[k] = j; // Store index of future reference
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }

                    int max = index[0];
                    pointer = 0;

                    // Determine which frame to replace
                    for (int j = 0; j < frames; j++) {
                        if (index[j] == 0) {
                            index[j] = Integer.MAX_VALUE; // If not referenced again, set to max
                        }
                        if (index[j] > max) {
                            max = index[j];
                            pointer = j; // Pointer to the frame to be replaced
                        }
                    }
                }

                // Replace the page
                buffer[pointer] = reference[i];
                fault++;
                
                // Manage pointer and fullness status
                if (!isFull) {
                    pointer++;
                    if (pointer == frames) {
                        pointer = 0;
                        isFull = true; // Mark as full once all frames are filled
                    }
                }
            }

            // Store the current state of buffer into memory layout for display later
            for (int j = 0; j < frames; j++) {
                mem_layout[i][j] = buffer[j];
            }
        }

        // Display memory layout for each step
        for (int i = 0; i < frames; i++) {
            for (int j = 0; j < ref_len; j++) {
                System.out.printf("%3d ", mem_layout[j][i]);
            }
            System.out.println();
        }

        // Display hit and fault details
        System.out.println("The number of Hits: " + hit);
        System.out.println("Hit Ratio: " + (float) hit / ref_len);
        System.out.println("The number of Faults: " + fault);
    }
}

