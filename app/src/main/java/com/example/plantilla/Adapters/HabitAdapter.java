package com.example.plantilla.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantilla.Activities.HabitsActivity;
import com.example.plantilla.Beans.Habit;
import com.example.plantilla.HabitPrefs;
import com.example.plantilla.R;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<com.example.plantilla.Adapters.HabitAdapter.HabitViewHolder> {

    private List<Habit> habitos;
    private OnItemClickListener listener;
    private Context context;

    private HabitPrefs habitPrefs;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onSeleccionCambio();
    }

    public HabitAdapter(List<Habit> habitos,Context context,  com.example.plantilla.Adapters.HabitAdapter.OnItemClickListener listener) {
        this.habitos = habitos;
        this.listener = listener;
        this.context = context;
        this.habitPrefs = new HabitPrefs(context);
    }

    @NonNull
    @Override
    public com.example.plantilla.Adapters.HabitAdapter.HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_habito, parent, false);
        return new HabitAdapter.HabitViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.plantilla.Adapters.HabitAdapter.HabitViewHolder holder, int position) {
        Habit habit = habitos.get(position);
        Long fechaInicio =  habit.getFechaInicio();

        holder.tvNombre.setText(habit.getNombre());
        holder.tvCategoria.setText("Categoría: " + habit.getCategoria());
        holder.tvFechaInicio.setText(android.text.format.DateFormat.format("Inicio: dd/MM/yyyy HH:mm", fechaInicio));
        holder.tvFrecuencia.setText("Cada " + habit.getFrecuencia() + " horas");

        // Implementar la eliminación de un hábito
        holder.btnDelete.setOnClickListener(v -> {
            habitPrefs.eliminar(position);  // Eliminar el hábito desde la lista en SharedPreferences
            habitos.remove(position);  // Eliminar el hábito de la lista de la interfaz
            notifyItemRemoved(position);
            if (context instanceof HabitsActivity && habitos.isEmpty()) {
                ((HabitsActivity) context).mostrarTextoNoHayHabitos();
            }// Actualizar el RecyclerView
        });

    }

    @Override
    public int getItemCount() {
        return habitos.size();
    }


    public Habit getItem(int position) {
        return habitos.get(position);
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCategoria, tvFecha, tvHora, tvFechaInicio, tvFrecuencia;
        Button btnDelete;

        public HabitViewHolder(@NonNull View itemView, final com.example.plantilla.Adapters.HabitAdapter.OnItemClickListener listener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFrecuencia = itemView.findViewById(R.id.tvFrecuencia);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
