import { Button, Input, Modal } from "antd";
import { useState } from "react";
import { usePostSendMentorRequest } from "../hooks/usePostSendMentorRequest.hook";

interface PrijavaModalProps {
  open: boolean;
  onClose: () => void;
  requesterId: number;
}

function PrijavaModal({ open, onClose, requesterId }: PrijavaModalProps) {
  const [reason, setReason] = useState("");

  const mutation = usePostSendMentorRequest({
    onSuccess: () => {
      alert("Prijava uspješno poslana!");
      onClose();
    },
  });

  const handleReasonChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setReason(e.target.value);
  };

  const handleSubmit = () => {
    if (!reason.trim()) {
      alert("Razlog prijave ne može biti prazan.");
      return;
    }

    mutation.mutate({
      comment: reason,
      requesterId,
    });
  };

  return (
    <Modal
      title="Prijava za mentorstvo"
      open={open}
      onCancel={onClose}
      footer={null}
    >
      <div style={{ marginBottom: 16 }}>
        <Input.TextArea
          rows={3}
          readOnly
          value="Opis što kako radi uloga mentora i koje su funkcionalnosti uloge"
          style={{
            backgroundColor: "#f0f5ff",
            borderColor: "#d6e4ff",
            color: "#000",
          }}
        />
      </div>
      <div style={{ marginBottom: 16 }}>
        <label
          style={{
            display: "block",
            fontWeight: "bold",
            marginBottom: 8,
          }}
        >
          Razlog prijave:
        </label>
        <Input.TextArea
          rows={6}
          placeholder="Unesite razlog prijave"
          value={reason}
          onChange={handleReasonChange}
        />
      </div>
      <div style={{ textAlign: "right" }}>
        <Button type="primary" onClick={handleSubmit}>
          Predaj prijavu
        </Button>
      </div>
    </Modal>
  );
}

export default PrijavaModal;
