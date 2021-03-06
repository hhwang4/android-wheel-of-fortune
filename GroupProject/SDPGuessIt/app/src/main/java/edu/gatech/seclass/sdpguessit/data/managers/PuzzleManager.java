package edu.gatech.seclass.sdpguessit.data.managers;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpguessit.data.models.Player;
import edu.gatech.seclass.sdpguessit.data.models.Puzzle;
import edu.gatech.seclass.sdpguessit.data.models.PuzzleRecord;
import edu.gatech.seclass.sdpguessit.data.models.Tournament;
import edu.gatech.seclass.sdpguessit.data.models.TournamentRecord;

public class PuzzleManager {
    public PuzzleManager() {
    }

    public Long createNewPuzzle(Player player, int maxGuesses, String mPhrase) {
        Puzzle puzzle = new Puzzle(player, mPhrase, maxGuesses);
        puzzle.save();
        return puzzle.getId();
    }

    public List<Puzzle> getPuzzles() {
        return Puzzle.listAll(Puzzle.class);
    }

    public List<Puzzle> getPlayablePuzzlesForUser(Player player){
        List<Puzzle> puzzles = getPuzzles();
        List<Puzzle> filtered = new ArrayList<>();

        for(Puzzle puzzle : puzzles){
            if(puzzle.getPlayer().getId() != player.getId()){
                PuzzleRecord puzzleRecord = getPuzzleRecord(player, puzzle);

                if(puzzleRecord == null || !puzzleRecord.isComplete()){
                    filtered.add(puzzle);
                }
            }
        }

        return filtered;
    }

    public List<Puzzle> getCreatedOrCompltedPuzzlesForUser(Player player){
        List<Puzzle> puzzles = getPuzzles();
        List<Puzzle> filtered = new ArrayList<>();

        for(Puzzle puzzle : puzzles){
            if(puzzle.getPlayer().getId() == player.getId()){
                filtered.add(puzzle);
            }else{
                PuzzleRecord puzzleRecord = getPuzzleRecord(player, puzzle);

                if(puzzleRecord != null && puzzleRecord.isComplete()){
                    filtered.add(puzzle);
                }
            }
        }

        return filtered;
    }

    public List<PuzzleRecord> getPuzzleRecords() {
        return PuzzleRecord.listAll(PuzzleRecord.class);
    }

    public List<PuzzleRecord> getPuzzleRecordsForPlayer(Player player) {
        List<PuzzleRecord> puzzleRecords = new ArrayList<>();

        for(PuzzleRecord puzzleRecord : getPuzzleRecords()){
            if(puzzleRecord.getPlayer().getId() == player.getId()){
                puzzleRecords.add(puzzleRecord);
            }
        }

        return puzzleRecords;
    }

    public List<PuzzleRecord> getPuzzleRecordsForPuzzle(Puzzle puzzle) {
        List<PuzzleRecord> puzzleRecords = new ArrayList<>();

        for(PuzzleRecord puzzleRecord : getPuzzleRecords()){
            if(puzzleRecord.getPuzzle().getId() == puzzle.getId()){
                puzzleRecords.add(puzzleRecord);
            }
        }

        return puzzleRecords;
    }

    public Puzzle getPuzzle(long id) {
        return Puzzle.findById(Puzzle.class, id);
    }

    public PuzzleRecord addNewPuzzleRecord(Player player, Puzzle puzzle) {
        PuzzleRecord puzzleRecord = null;
        for (PuzzleRecord pr : getPuzzleRecords()) {
            if(pr.getPuzzle().getId() == puzzle.getId() && pr.getPlayer().getId() == player.getId()){
                puzzleRecord = pr;
                break;
            }
        }

        if(puzzleRecord == null) {
            puzzleRecord = new PuzzleRecord(player, puzzle, 0, "", puzzle.getMaxGuesses(), false);
        }

        return puzzleRecord;
    }

    public boolean doesPuzzleRecordExist(Player player, Puzzle puzzle){
        return getPuzzleRecord(player, puzzle) != null;
    }

    public PuzzleRecord getPuzzleRecord(Player player, Puzzle puzzle) {
        List<PuzzleRecord> puzzleRecords = getPuzzleRecords();
        for(PuzzleRecord  puzzleRecord : puzzleRecords){
            if(puzzleRecord.getPlayer().getId() == player.getId() && puzzleRecord.getPuzzle().getId() == puzzle.getId()){
                return puzzleRecord;
            }
        }

        return null;
    }
}
