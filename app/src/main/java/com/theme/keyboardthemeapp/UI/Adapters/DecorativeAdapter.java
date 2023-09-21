package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Dialogs.FancyTextDialog;
import com.theme.keyboardthemeapp.R;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DecorativeAdapter extends RecyclerView.Adapter<DecorativeAdapter.MyViewHolder> {
    private final Context context;
    private final String[] nameStyle;
    private final String text;

    public DecorativeAdapter(Context context, String[] nameStyle, String text) {
        this.context = context;
        this.nameStyle = nameStyle;
        this.text = text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_joke, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtJokeQuote.setTextColor(context.getResources().getColor(R.color.black));
        holder.TxtJokeQuote.setGravity(Gravity.CENTER);
        holder.TxtJokeQuote.setPadding((int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp));

        holder.TxtJokeQuote.setText(nameStyle[position].replace("abc",text));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyTextDialog fancyTextDialog = new FancyTextDialog(context, new FancyTextDialog.FancyTextListener() {
                    @Override
                    public void onCopy(FancyTextDialog fancyTextDialog) {
                        fancyTextDialog.dismiss();
                        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        manager.setPrimaryClip(ClipData.newPlainText("simple text", holder.TxtJokeQuote.getText().toString()));
                        Toast.makeText(context, "copy successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShare(FancyTextDialog fancyTextDialog) {
                        fancyTextDialog.dismiss();
                        Intent intentQuoteShare = new Intent(Intent.ACTION_SEND);
                        intentQuoteShare.setType("text/plain");
                        intentQuoteShare.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                        intentQuoteShare.putExtra(Intent.EXTRA_TEXT, holder.TxtJokeQuote.getText().toString());
                        context.startActivity(intentQuoteShare);
                    }

                    @Override
                    public void onWhastapp(FancyTextDialog fancyTextDialog) {
                        fancyTextDialog.dismiss();
                        GotoSharePackage(context, "com.whatsapp", holder.TxtJokeQuote.getText().toString());
                    }
                });
                fancyTextDialog.show();
                WindowManager.LayoutParams attributes = fancyTextDialog.getWindow().getAttributes();
                Window DialogWindow = fancyTextDialog.getWindow();
                attributes.copyFrom(DialogWindow.getAttributes());
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
                attributes.gravity = Gravity.CENTER;
                DialogWindow.setAttributes(attributes);
            }
        });
    }

    private void GotoSharePackage(Context context, String packageName, String statusTxt) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage(packageName);
            intent.putExtra(Intent.EXTRA_TEXT, statusTxt);
            context.startActivity(Intent.createChooser(intent, "Share with"));
        } catch (Exception e) {
            Toast.makeText(this.context, "App not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return nameStyle.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TxtJokeQuote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtJokeQuote = itemView.findViewById(R.id.TxtJokeQuote);
        }
    }
}