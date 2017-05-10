package parsers;

import java.util.Arrays;

import static parsers.Converter.hexByteArrayToInt;

/**
 * Created by Pang on 4/05/17.
 * Race data status message parser
 */
public class RaceStartStatusParser {


    public RaceStartStatusParser(byte[] body) {
        Integer startTime = hexByteArrayToInt(Arrays.copyOfRange(body, 9, 15));
        System.out.println("start time" + startTime);

    }
}