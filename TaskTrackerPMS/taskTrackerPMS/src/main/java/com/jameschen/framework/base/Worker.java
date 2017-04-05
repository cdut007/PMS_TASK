/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jameschen.framework.base;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.jameschen.comm.utils.Log;

/**
 * <p>
 * A Worker constrains data with a Workering pattern.
 * </p>
 * 
 * . Any call to one of these methods will cancel any previous non-executed
 * Workering request.</p>
 * 
 * 
 */
public abstract class Worker {

	public abstract interface MyWorkerable {
		public abstract Worker getWorker();
	}

	private static final String LOG_TAG = "Worker";

	private static final String THREAD_NAME = "Worker";
	private static final int WORK_TOKEN = 0xD0D0F00D;
	private static final int FINISH_TOKEN = 0xDEADBEEF;

	private Handler mThreadHandler;
	private Handler mResultHandler;

	private Delayer mDelayer;

	private final Object mLock = new Object();

	/**
	 * <p>
	 * Creates a new asynchronous Worker.
	 * </p>
	 */
	public Worker() {
		mResultHandler = new ResultsHandler();
	}

	/**
	 * <p>
	 * Starts an asynchronous Workering operation. Calling this method cancels
	 * all previous non-executed Workering requests and posts a new Workering
	 * request that will be executed later.
	 * </p>
	 * 
	 * @param intent
	 *            the intent used to Worker the data
	 * 
	 * @see #Worker(Intent, android.widget.Worker.WorkerListener)
	 */
	public final void workToken(Intent intent) {
		workToken(intent, null);
	}

	/**
	 * <p>
	 * Starts an asynchronous Workering operation. Calling this method posts a
	 * new Workering request that will be executed later.
	 * </p>
	 * 
	 * <p>
	 * Upon completion, the listener is notified.
	 * </p>
	 * 
	 * @param intent
	 *            the intent used to Worker the data
	 * @param listener
	 *            a listener notified upon completion of the operation
	 * 
	 * @see #Worker(Intent)
	 * @see #performWorkering(Intent)
	 * @see #publishResults(Intent, android.widget.Worker.WorkerResults)
	 */
	public final void workToken(Intent intent, WorkerListener listener) {
		synchronized (mLock) {
			if (mThreadHandler == null) {
				HandlerThread thread = new HandlerThread(THREAD_NAME,
						android.os.Process.THREAD_PRIORITY_BACKGROUND);
				thread.start();
				mThreadHandler = new RequestHandler(thread.getLooper());
			}

			final long delay = (mDelayer == null) ? 0 : mDelayer
					.getPostingDelay(intent);

			Message message = mThreadHandler.obtainMessage(WORK_TOKEN);

			RequestArguments args = new RequestArguments();
			// make sure we use an immutable copy of the intent, so that
			// it doesn't change while the Worker operation is in progress
			args.intent = intent != null ? intent : null;
			args.listener = listener;
			message.obj = args;

			// mThreadHandler.removeMessages(Worker_TOKEN);
			mThreadHandler.removeMessages(FINISH_TOKEN);

			mThreadHandler.sendMessageDelayed(message, delay);
		}
	}

	public void abortTask() {
		// TODO Auto-generated method stub
		if (mThreadHandler != null) {
			mThreadHandler.removeCallbacksAndMessages(null);
		}
	}

	/**
	 * Provide an interface that decides how long to delay the message for a
	 * given query. Useful for heuristics such as posting a delay for the delete
	 * key to avoid doing any work while the user holds down the delete key.
	 * 
	 * @param delayer
	 *            The delayer.
	 * @hide
	 */
	public void setDelayer(Delayer delayer) {
		synchronized (mLock) {
			mDelayer = delayer;
		}
	}

	/**
	 * <p>
	 * Invoked in a worker thread to Worker the data according to the intent.
	 * Subclasses must implement this method to perform the Workering operation.
	 * Results computed by the Workering operation must be returned as a
	 * {@link android.widget.Worker.WorkerResults} that will then be published
	 * in the UI thread through
	 * {@link #publishResults(Intent, android.widget.Worker.WorkerResults)} .
	 * </p>
	 * 
	 * <p>
	 * <strong>Contract:</strong> When the intent is null, the original data
	 * must be restored.
	 * </p>
	 * 
	 * @param intent
	 *            the intent used to Worker the data
	 * @return the results of the Workering operation
	 * 
	 * @see #Worker(Intent, android.widget.Worker.WorkerListener)
	 * @see #publishResults(Intent, android.widget.Worker.WorkerResults)
	 * @see android.widget.Worker.WorkerResults
	 */
	protected abstract WorkerResults performWorkering(Intent intent);

	/**
	 * <p>
	 * Invoked in the UI thread to publish the Workering results in the user
	 * interface. Subclasses must implement this method to display the results
	 * computed in {@link #performWorkering}.
	 * </p>
	 * 
	 * @param intent
	 *            the intent used to Worker the data
	 * @param results
	 *            the results of the Workering operation
	 * 
	 * @see #Worker(Intent, android.widget.Worker.WorkerListener)
	 * @see #performWorkering(Intent)
	 * @see android.widget.Worker.WorkerResults
	 */
	protected abstract void publishResults(Intent intent, WorkerResults results);

	/**
	 * <p>
	 * Converts a value from the Workered set into a Intent. Subclasses should
	 * override this method to convert their results. The default implementation
	 * returns an empty String for null values or the default String
	 * representation of the value.
	 * </p>
	 * 
	 * @param resultValue
	 *            the value to convert to a Intent
	 * @return a Intent representing the value
	 */
	public String convertResultToString(Object resultValue) {
		return resultValue == null ? "" : resultValue.toString();
	}

	/**
	 * <p>
	 * Holds the results of a Workering operation. The results are the values
	 * computed by the Workering operation and the number of these values.
	 * </p>
	 */
	protected static class WorkerResults {
		public WorkerResults() {
			// nothing to see here
		}

		/**
		 * <p>
		 * Contains all the values computed by the Workering operation.
		 * </p>
		 */
		public Object values;

		/**
		 * <p>
		 * Contains the number of values computed by the Workering operation.
		 * </p>
		 */
		public int count;
	}

	/**
	 * <p>
	 * Listener used to receive a notification upon completion of a Workering
	 * operation.
	 * </p>
	 */
	public static interface WorkerListener {
		/**
		 * <p>
		 * Notifies the end of a Workering operation.
		 * </p>
		 * 
		 * @param count
		 *            the number of values computed by the Worker
		 */
		public void onWorkerComplete(int count);
	}

	/**
	 * <p>
	 * Worker thread handler. When a new Workering request is posted from
	 * {@link android.widget.Worker#Worker(Intent, android.widget.Worker.WorkerListener)}
	 * , it is sent to this handler.
	 * </p>
	 */
	private class RequestHandler extends Handler {
		public RequestHandler(Looper looper) {
			super(looper);
		}

		/**
		 * <p>
		 * Handles Workering requests by calling {@link Worker#performWorkering}
		 * and then sending a message with the results to the results handler.
		 * </p>
		 * 
		 * @param msg
		 *            the Workering request
		 */
		public void handleMessage(Message msg) {
			int what = msg.what;
			Message message;
			switch (what) {
			case WORK_TOKEN:
				RequestArguments args = (RequestArguments) msg.obj;
				try {
					args.results = performWorkering(args.intent);
				} catch (Exception e) {
					args.results = new WorkerResults();
					Log.w(LOG_TAG,
							"An exception occured during performWorkering()!"+
							e.getLocalizedMessage());
				} finally {
					message = mResultHandler.obtainMessage(what);
					message.obj = args;
					message.sendToTarget();
				}

				synchronized (mLock) {
					if (mThreadHandler != null) {
						Message finishMessage = mThreadHandler
								.obtainMessage(FINISH_TOKEN);
						mThreadHandler.sendMessageDelayed(finishMessage, 3000);
					}
				}
				break;
			case FINISH_TOKEN:
				synchronized (mLock) {
					if (mThreadHandler != null) {
						mThreadHandler.getLooper().quit();
						mThreadHandler = null;
					}
				}
				break;
			}
		}
	}

	/**
	 * <p>
	 * Handles the results of a Workering operation. The results are handled in
	 * the UI thread.
	 * </p>
	 */
	private class ResultsHandler extends Handler {
		/**
		 * <p>
		 * Messages received from the request handler are processed in the UI
		 * thread. The processing involves calling
		 * {@link Worker#publishResults(Intent, android.widget.Worker.WorkerResults)}
		 * to post the results back in the UI and then notifying the listener,
		 * if any.
		 * </p>
		 * 
		 * @param msg
		 *            the Workering results
		 */
		@Override
		public void handleMessage(Message msg) {
			RequestArguments args = (RequestArguments) msg.obj;

			publishResults(args.intent, args.results);
			if (args.listener != null) {
				int count = args.results != null ? args.results.count : -1;
				args.listener.onWorkerComplete(count);
			}
		}
	}

	/**
	 * <p>
	 * Holds the arguments of a Workering request as well as the results of the
	 * request.
	 * </p>
	 */
	private static class RequestArguments {
		/**
		 * <p>
		 * The intent used to Worker the data.
		 * </p>
		 */
		Intent intent;

		/**
		 * <p>
		 * The listener to notify upon completion. Can be null.
		 * </p>
		 */
		WorkerListener listener;

		/**
		 * <p>
		 * The results of the Workering operation.
		 * </p>
		 */
		WorkerResults results;
	}

	/**
	 * @hide
	 */
	public interface Delayer {

		/**
		 * @param intent
		 *            The intent passed to {@link Worker#Worker(Intent)}
		 * @return The delay that should be used for
		 *         {@link Handler#sendMessageDelayed(android.os.Message, long)}
		 */
		long getPostingDelay(Intent intent);
	}

}
