package dicoding.adrian.submission3.Movie;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import dicoding.adrian.submission3.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<MovieItems> mData = new ArrayList<>();
    private OnItemClickListener listener;

    void setData(ArrayList<MovieItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_items, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.textViewTitle.setText(mData.get(position).getTitle());
        movieViewHolder.textViewOverview.setText(mData.get(position).getOverview());
        String releaseDate = mData.get(position).getReleased();
        String releasedYear = releaseDate.substring(0, 4);
        movieViewHolder.textViewReleased.setText(releasedYear);
        double score = mData.get(position).getScore() * 10;
        movieViewHolder.score.setRating((float) ((score * 5) / 100));
        movieViewHolder.textViewScore.setText(String.valueOf((int) score));
        String uri = "https://image.tmdb.org/t/p/original" + mData.get(position).getPoster();
        Glide.with(movieViewHolder.itemView.getContext())
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        movieViewHolder.progressBarItemMovie.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(movieViewHolder.poster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView poster;
        TextView textViewReleased;
        TextView textViewOverview;
        RatingBar score;
        TextView textViewScore;
        ProgressBar progressBarItemMovie;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_item_title);
            poster = itemView.findViewById(R.id.img_item_poster);
            textViewReleased = itemView.findViewById(R.id.tv_item_releasedYear);
            textViewOverview = itemView.findViewById(R.id.tv_item_overview);
            textViewScore = itemView.findViewById(R.id.tv_item_scoreAngkaHome);
            score = itemView.findViewById(R.id.scoreHome);
            progressBarItemMovie = itemView.findViewById(R.id.progressBar_itemMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mData.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MovieItems movieItems);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
