package com.zeefive.vmcapp.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.Tool;

public class ToolsViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titleView1;
    public TextView titleView2;

    public ToolsViewHolder(View view) {
        super(view);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        titleView1 = (TextView) view.findViewById(R.id.textView1);
        titleView2 = (TextView) view.findViewById(R.id.textView2);
    }

    public void bindToPost(Tool item, Context context) {

        imageView.setVisibility(View.VISIBLE);

        switch (item.getTitle()){
            case "Undu Vandi 1": imageView.setImageResource(R.drawable.tool_undu_vandi); break;
            case "Lift": imageView.setImageResource(R.drawable.tool_lift); break;
            case "Wire": imageView.setImageResource(R.drawable.tool_wire); break;
            case "Kotta": imageView.setImageResource(R.drawable.tool_kotta); break;
            case "Kaikotte": imageView.setImageResource(R.drawable.tool_kaikote); break;
            case "Undu Vandi 2": imageView.setImageResource(R.drawable.tool_undu_vandi); break;
            case "Hilti": imageView.setImageResource(R.drawable.hilti); break;
            case "Thoni": imageView.setImageResource(R.drawable.tool_thoni); break;
            case "Thaparia Steel Cutter": imageView.setImageResource(R.drawable.tool_rod_cutter); break;
            case "Ladder (Heavy)": imageView.setImageResource(R.drawable.tool_ladder); break;
            case "Water Net X 2": imageView.setImageResource(R.drawable.tool_net); break;
            case "Vibrator Old": imageView.setImageResource(R.drawable.tool_vibrator); break;
            case "Vibrator New": imageView.setImageResource(R.drawable.tool_vibrator); break;
            case "Makkitta Marble Cutter": imageView.setImageResource(R.drawable.tool_marble_cutter); break;
            case "Rope Heavy": imageView.setImageResource(R.drawable.tool_rope); break;
            case "DeWalt Hand Cutter": imageView.setImageResource(R.drawable.tool_hand_cutter); break;
            case "Concrete Sheet": imageView.setImageResource(R.drawable.tool_sheet); break;
            case "Mixer Machine": imageView.setImageResource(R.drawable.tool_mixer_machine); break;
            case "Padave Uyaram Kambi": imageView.setImageResource(R.drawable.tool_padave_kambi); break;
            case "Ken Marble Cutter": imageView.setImageResource(R.drawable.tool_marble_cutter); break;
            case "Pulley X 2" : imageView.setImageResource(R.drawable.tool_pulley); break;
            case "Drum Red" : imageView.setImageResource(R.drawable.tool_drum); break;
            default: imageView.setVisibility(View.GONE); break;
        }

        titleView1.setText(item.getTitle());
        titleView2.setText(item.getLocation());
    }
}
