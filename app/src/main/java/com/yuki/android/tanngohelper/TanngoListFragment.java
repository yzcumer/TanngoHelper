package com.yuki.android.tanngohelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fxf on 2016/10/25.
 */

public class TanngoListFragment extends Fragment {
    private RecyclerView mTanngoRecyclerView;
    private TanngoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tanngo_list, container, false);
        mTanngoRecyclerView = (RecyclerView) view.findViewById(R.id.tanngo_recycler_view);
        mTanngoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_tanngo_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_tanngo:
                Tanngo tanngo = new Tanngo();
                TanngoLab.get(getActivity()).addTanngo(tanngo);
                Intent intent = TanngoPagerActivity
                        .newIntent(getActivity(), tanngo.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        TanngoLab tanngoLab = TanngoLab.get(getActivity());
        List<Tanngo> tanngos = tanngoLab.getTanngos();

        if (mAdapter == null) {
            mAdapter = new TanngoAdapter(tanngos);
            mTanngoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTanngos(tanngos);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class TanngoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mWordTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Tanngo mTanngo;

        public TanngoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mWordTextView = (TextView) itemView.findViewById(R.id.list_item_tanngo_word_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_tanngo_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_tanngo_solved_check_box);
        }

        public void bindTanngo(Tanngo tanngo) {
            mTanngo = tanngo;
            mWordTextView.setText(mTanngo.getWord());
            mDateTextView.setText(mTanngo.getDate().toString());
            mSolvedCheckBox.setChecked(mTanngo.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TanngoPagerActivity.newIntent(getActivity(), mTanngo.getId());
            startActivity(intent);
        }
    }


    private class TanngoAdapter extends RecyclerView.Adapter<TanngoHolder> {
        private List<Tanngo> mTanngos;

        public TanngoAdapter(List<Tanngo> tanngos) {
            mTanngos = tanngos;
        }

        @Override
        public TanngoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_tanngo, parent, false);
            return new TanngoHolder(view);
        }

        @Override
        public void onBindViewHolder(TanngoHolder holder, final int position) {
            Tanngo tanngo = mTanngos.get(position);
            holder.bindTanngo(tanngo);
        }

        @Override
        public int getItemCount() {
            return mTanngos.size();
        }

        public void setTanngos(List<Tanngo> tanngos) {
            mTanngos = tanngos;
        }
    }
}




