import { Modal, Input, Button } from "antd";
import { useState } from "react";

interface PrijavaModalProps {
  open: boolean;
  onClose: () => void;
}

const PrijavaModal: React.FC<PrijavaModalProps> = ({ open, onClose }) => {
  const [reason, setReason] = useState("");

  const handleReasonChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setReason(e.target.value);
  };

  const handleSubmit = () => {
    console.log("Razlog prijave:", reason);
    onClose();
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
};

export default PrijavaModal;
