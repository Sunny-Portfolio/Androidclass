package com.example.bytecrunch.helper;

import android.os.AsyncTask;
import android.widget.ImageView;

public class AllAsyncTask extends AsyncTask<Integer, String, Integer> {
    @Override
    protected Integer doInBackground(Integer... integers) {
        return null;
    }
}

//
///**
// * Async Task for AI's moves
// */
//private class AImove extends AsyncTask<Integer, String, Integer> {
//
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    /**
//     * AI's move goes here
//     *
//     * @param integers The parameters of the task.
//     * @return Grid position index
//     */
//    @Override
//    protected Integer doInBackground(Integer... integers) {
//        // AI's move. Added random time delay to be more realistic
//        try {
//            int randomTime = (int) (Math.random() * 3000);
//            Thread.sleep(randomTime);
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (insane_mode_on)
//            return makeAIDecision();
//        else
//            return makeRandomDecision();
//    }
//
//    @Override
//    protected void onProgressUpdate(String... values) {
//        super.onProgressUpdate(values);
//    }
//
//    /**
//     * Execute AI move by passing ImageView box to playMove()
//     * @param boxIndex The result of the operation computed by {@link #doInBackground}.
//     *
//     */
//    @Override
//    protected void onPostExecute(Integer boxIndex) {
//        super.onPostExecute(boxIndex);
//
//        if (boxIndex != -1) {
//            // Send AI chosen box to playMove
//            int resId = getResources().getIdentifier("ID_Box" + (boxIndex + 1), "id", getPackageName());
//            ImageView box = findViewById(resId);
//            playMove(box);
//        }
//    }