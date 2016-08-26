package knockknock.delivr_it.knocknock.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.models.Notification;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.HolderView> {

    private List<Notification> notifications;

    public NotificationListAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_single, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.notificationMessage.setText(notifications.get(0).getNotificationText());
        Date date = new Date(notifications.get(0).getTimeOfReceipt());
        String receiptDateTime = new SimpleDateFormat("hh:mm a\t\tdd/MM/yyyy", Locale.ENGLISH).format(date);
        holder.notificationDateTime.setText("Received at\n" + receiptDateTime);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        TextView notificationMessage;
        TextView notificationDateTime;

        public HolderView(View itemView) {
            super(itemView);
            notificationMessage = (TextView) itemView.findViewById(R.id.notification_message);
            notificationDateTime = (TextView) itemView.findViewById(R.id.notification_date_time);
        }
    }
}
