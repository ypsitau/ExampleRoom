package io.github.ypsitau.exampleroom;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	public AppDatabase db;
	public Button button_add;
	public Button button_show;
	public EditText editText_log;
	private static class AsyncTask_addUsers extends AsyncTask<Void, Void, Integer> {
		MainActivity mainActivity;
		public AsyncTask_addUsers(MainActivity mainActivity) {
			this.mainActivity = mainActivity;
		}

		@Override
		protected Integer doInBackground(Void... voids) {
			mainActivity.db.userDao().insertMulti(
					new User("first1", "last1"),
					new User("first2", "last2"),
					new User("first3", "last3"),
					new User("first4", "last4"),
					new User("first5", "last5"),
					new User("first6", "last6"),
					new User("first7", "last7"),
					new User("first8", "last8"));
			return mainActivity.db.userDao().countAll();
		}

		@Override
		protected void onPostExecute(Integer n) {
			Toast toast = Toast.makeText(mainActivity,
					String.format("Current items:%d", n),
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	private static class AsyncTask_showUsers extends AsyncTask<Void, Void, Void> {
		MainActivity mainActivity;
		List<User> users;
		public AsyncTask_showUsers(MainActivity mainActivity) {
			this.mainActivity = mainActivity;
		}

		@Override
		protected Void doInBackground(Void... voids) {
			users = mainActivity.db.userDao().getAll();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			mainActivity.printf("List of users: %d\n", users.size());
			for (User user : users) {
				mainActivity.printf("%s %s\n", user.getFirstName(), user.getLastName());
			}
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button_add = findViewById(R.id.button_add);
		button_show = findViewById(R.id.button_show);
		editText_log = findViewById(R.id.editText_log);
		db = Room.databaseBuilder(getApplicationContext(),
				AppDatabase.class, "exampleroom.sqlite3").build();
		final MainActivity mainActivity = this;
		button_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTask_addUsers(mainActivity).execute();
			}
		});
		button_show.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTask_showUsers(mainActivity).execute();
			}
		});
		editText_log.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editText_log.setText("");
				return false;
			}
		});
	}

	public void printf(String format, Object... args) {
		editText_log.append(String.format(format, args));
		editText_log.setSelection(editText_log.getText().length());
	}
}
