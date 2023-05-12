package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttodoapp.R;
import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.List;


public class TodoListFragment extends Fragment {
    View rootView;
    TextView mPriority;  // for the priority case of the task
    private TodoViewModel mTodoViewModel;
    RecyclerView todoRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoRecyclerView = rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        todoRecyclerView.setLayoutManager(layoutManager);
        updateRV();
        return rootView;
    }


    //for displaying todo list in main page using the adapter
    void updateRV() {
        mTodoViewModel.getAllTodos().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> todoList) {
                TodoAdapter adapter = new TodoAdapter(todoList);
                todoRecyclerView.setAdapter(adapter);
            }
        });
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {
        List<Task> mTodoList;

        public TodoAdapter(List<Task> todoList) {
            mTodoList = todoList;
        }

        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new TodoHolder(layoutInflater, parent);
        }

        //for changing the color of todo list according to the priority
        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            Task todo = mTodoList.get(position);
            LinearLayout layout = (LinearLayout) ((ViewGroup) holder.mTitle.getParent());

            //code for the priority basis of the task
            String priorityString;
            switch (todo.getPriority()) {
                case 1:
                    priorityString = "High";
                    break;
                case 2:
                    priorityString = "Medium";
                    break;
                case 3:
                    priorityString = "Low";
                    break;
                default:
                    priorityString = "Unknown";
            }

            mPriority.setText(priorityString);


            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return mTodoList.size();
        }

        public Task getTodo(int index) {
            return mTodoList.get(index);
        }

        public Task getTodoAt(int index) {
            return mTodoList.get(index);
        }
    }

    private class TodoHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mDate;
        TextView mDesprition;
        TextView mIsCompleted;

        public TodoHolder(LayoutInflater inflater, ViewGroup parentViewGroup) {
            super(inflater.inflate(R.layout.list_item_todo, parentViewGroup, false));
            mTitle = itemView.findViewById(R.id.list_title);
            mDate = itemView.findViewById(R.id.list_date);
            mDesprition = itemView.findViewById(R.id.list_description);
            mPriority = itemView.findViewById(R.id.list_priority);  //for priority of the code
            mIsCompleted = itemView.findViewById(R.id.list_is_completed);   //for status of the task
            String priorityString;
            Task todo = new Task();
            switch (todo.getPriority()) {
                case 1:
                    priorityString = "High";
                    break;
                case 2:
                    priorityString = "Medium";
                    break;
                case 3:
                    priorityString = "Low";
                    break;
                default:
                    priorityString = "Unknown";
            }
            mPriority.setText(priorityString);


            //for updating the list while the user clicks the todo
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adapter = new TodoAdapter(mTodoViewModel.getAllTodos().getValue());
                    int position = getAdapterPosition();
                    Task task = adapter.getTodoAt(position);
                    Intent intent = new Intent(getActivity(), EditTodoActivity.class);
                    intent.putExtra("TodoId", task.getId());
                    startActivity(intent);
                }
            });
            mDesprition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adapter = new TodoAdapter(mTodoViewModel.getAllTodos().getValue());
                    int position = getAdapterPosition();
                    Task task = adapter.getTodoAt(position);
                    Intent intent = new Intent(getActivity(), EditTodoActivity.class);
                    intent.putExtra("TodoId", task.getId());
                    startActivity(intent);
                }
            });
            mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adapter = new TodoAdapter(mTodoViewModel.getAllTodos().getValue());
                    int position = getAdapterPosition();
                    Task task = adapter.getTodoAt(position);
                    Intent intent = new Intent(getActivity(), EditTodoActivity.class);
                    intent.putExtra("TodoId", task.getId());
                    startActivity(intent);
                }
            });
        }

        //For displaying the list
        public void bind(Task todo) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            mTitle.setText(todo.getTitle());
            mDesprition.setText(todo.getDescription());
            mDate.setText(dateFormatter.format(todo.getCreatedDate()));
            if (todo.isIs_completed()) {
                mIsCompleted.setText("Completed");
            } else {
                mIsCompleted.setText("Not completed");
            }
        }
    }
}
