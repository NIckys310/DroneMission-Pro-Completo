// ============================================================================================
// DOCUMENTACIÓN · src/components/ConfirmDialog.tsx
//
// QUÉ ES:        La ventanita de "¿Estás seguro?".
// QUÉ HACE:      Pide confirmar antes de borrar o cancelar; puede pedir un texto (como
//                el motivo de cancelación).
// SI LO CAMBIAS: danger=true pinta el botón de rojo.
// ============================================================================================
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from '@mui/material';
import { useEffect, useState } from 'react';

interface Props {
  /** Abierto. */
  open: boolean;
  /** Título. */
  title: string;
  /** Mensaje. */
  message: string;
  /** Etiqueta de confirmación (opcional). */
  confirmLabel?: string;
  /** Peligro (opcional). */
  danger?: boolean;
  /** Si se indica, pide un texto (por ejemplo, el motivo de cancelación). */
  inputLabel?: string;
  /** Required de entrada (opcional). */
  inputRequired?: boolean;
  /** El confirmación. */
  onConfirm: (text: string) => Promise<void> | void;
  /** El close. */
  onClose: () => void;
}

/** Componente React ConfirmDialog: lo que se ve en pantalla. Props: open, title, message, confirmLabel, danger, inputLabel, inputRequired, onConfirm, onClose. */
export default function ConfirmDialog({ open, title, message, confirmLabel = 'Confirmar', danger, inputLabel, inputRequired, onConfirm, onClose }: Props) {
  // Estado del componente: text.
  const [text, setText] = useState('');
  // Estado del componente: busy.
  const [busy, setBusy] = useState(false);
  useEffect(() => { if (open) setText(''); }, [open]);

  const handleConfirm = async () => {
    setBusy(true);
    try {
      await onConfirm(text);
    } finally {
      setBusy(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <DialogContentText>{message}</DialogContentText>
        {inputLabel && (
          <TextField autoFocus multiline minRows={2} sx={{ mt: 2 }} label={inputLabel} value={text}
            onChange={(e) => setText(e.target.value)} inputProps={{ maxLength: 500 }} />
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancelar</Button>
        <Button variant="contained" color={danger ? 'error' : 'primary'} onClick={handleConfirm}
          disabled={busy || (inputRequired && !text.trim())}>
          {confirmLabel}
        </Button>
      </DialogActions>
    </Dialog>
  );
}
