

import java.io.*;

public class fifo {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int frames, pointer = 0, hit = 0, fault = 0, ref_len;
        int[] buffer;
        int[] reference;
        int[][] mem_layout;

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
                buffer[pointer] = reference[i]; // Replace the oldest page
                fault++;
                pointer++;

                // If pointer reaches the end of the buffer, reset to 0 (FIFO circular queue)
                if (pointer == frames) {
                    pointer = 0;
                }
            }

            // Store the current state of buffer into memory layout for display later
            for (int j = 0; j < frames; j++) {
                mem_layout[i][j] = buffer[j];
            }
        }

        // Display memory layout for each step
        for (int i = 0; i < ref_len; i++) {
            for (int j = 0; j < frames; j++) {
                System.out.printf("%3d ", mem_layout[i][j]);
            }
            System.out.println();
        }

        // Display hit and fault details
        System.out.println("The number of Hits: " + hit);
        System.out.println("Hit Ratio: " + (float) hit / ref_len);
        System.out.println("The number of Faults: " + fault);
    }
}
