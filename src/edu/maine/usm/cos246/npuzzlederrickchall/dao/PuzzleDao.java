package edu.maine.usm.cos246.npuzzlederrickchall.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PuzzleDao {

  // Database fields
  private SQLiteDatabase database;
  private SQLiteManager dbHelper;
  private String[] allColumns = { SQLiteManager.COLUMN_ID,
		  SQLiteManager.COLUMN_IMAGE };

  public PuzzleDao(Context context) {
    dbHelper = new SQLiteManager(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Puzzle createPuzzle(String comment) {
    ContentValues values = new ContentValues();
    values.put(SQLiteManager.COLUMN_IMAGE, comment);
    long insertId = database.insert(SQLiteManager.TABLE_PUZZLE, null,
        values);
    Cursor cursor = database.query(SQLiteManager.TABLE_PUZZLE,
        allColumns, SQLiteManager.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Puzzle puzzle = cursorToPuzzle(cursor);
    cursor.close();
    return puzzle;
  }

  public void deletePuzzle(Puzzle puzzle) {
    long id = puzzle.getId();
    System.out.println("Puzzle deleted with id: " + id);
    database.delete(SQLiteManager.TABLE_PUZZLE, SQLiteManager.COLUMN_ID
        + " = " + id, null);
  }

  public List<Puzzle> getAllPuzzles() {
    List<Puzzle> puzzles = new ArrayList<Puzzle>();

    Cursor cursor = database.query(SQLiteManager.TABLE_PUZZLE,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
    	Puzzle puzzle = cursorToPuzzle(cursor);
    	puzzles.add(puzzle);
    	cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return puzzles;
  }

  private Puzzle cursorToPuzzle(Cursor cursor) {
    Puzzle puzzle = new Puzzle();
    puzzle.setId(cursor.getLong(0));
    puzzle.setImage(cursor.getString(1));
    return puzzle;
  }
} 