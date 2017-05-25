package parsers.xml.race;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by psu43 on 26/04/17.
 * Race data contains course boundaries, race participants, race type, and the course description.
 */
public class RaceData {

    private List<YachtData> participants = new ArrayList<>();
    private Set<Integer> participantIDs = new HashSet<>();
    private List<MarkData> startMarks = new ArrayList<>();
    private List<MarkData> finishMarks = new ArrayList<>();
    private List<CompoundMarkData> course = new ArrayList<>();
    private List<CornerData> compoundMarkSequence = new ArrayList<>();
    private Set<Integer> markIDs = new HashSet<>();
    private List<LimitData> courseLimit = new ArrayList<>();


    RaceData() {}

    /**
     * Gets a list of start marks ID from the startMarks list
     * @return List list of start marks ids
     */
    public List<Integer> getStartMarksID(){
        List<Integer> returnList=new ArrayList<>();
        for(MarkData mark:startMarks){
            returnList.add(mark.getSourceID());
        }
        return returnList;
    }

    /**
     * From the finish marks list, get the finish marks id
     * @return List the list of finish mark ids
     */
    public List<Integer> getFinishMarksID(){
        List<Integer> returnList=new ArrayList<>();
        for(MarkData mark:finishMarks){
            returnList.add(mark.getSourceID());
        }
        return returnList;
    }

    void setStartMarks(List<MarkData> startMarks) {
        this.startMarks = startMarks;
    }

    void setFinishMarks(List<MarkData> finishMarks) {
        this.finishMarks = finishMarks;
    }

    public List<CompoundMarkData> getCourse() {
        return course;
    }

    public void setCourse(List<CompoundMarkData> course) {
        this.course = course;
    }

    List<YachtData> getParticipants() {
        return participants;
    }

    void setParticipantIDs(Set<Integer> participantIDs) {
        this.participantIDs = participantIDs;
    }

    List<CornerData> getCompoundMarkSequence() {
        return compoundMarkSequence;
    }

    List<LimitData> getCourseLimit() {
        return courseLimit;
    }

    void addCourseLimit(LimitData limitData) {
        this.courseLimit.add(limitData);
    }

    public Set<Integer> getParticipantIDs() {
        return participantIDs;
    }

    public Set<Integer> getMarkIDs() {
        return markIDs;
    }

    void addMarkID(Integer markId) {
        this.markIDs.add(markId);
    }
}
