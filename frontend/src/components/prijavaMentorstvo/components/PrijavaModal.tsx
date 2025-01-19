import { Button, Input, Modal } from "antd";
import { useState } from "react";
import { usePostSendMentorRequest } from "../hooks/usePostSendMentorRequest.hook";
import { useQueryClient } from "@tanstack/react-query";

interface PrijavaModalProps {
  open: boolean;
  onClose: () => void;
  requesterId: number;
}

function PrijavaModal({ open, onClose, requesterId }: PrijavaModalProps) {
  const [reason, setReason] = useState("");

  const queryClient = useQueryClient();
  const mutation = usePostSendMentorRequest({
    onSuccess: () => {
      onClose();
      queryClient.invalidateQueries({ queryKey: ["mentorRequests"] });
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
      centered
    >
      <div style={{ marginBottom: 16 }}>
        <Input.TextArea
          rows={8}
          readOnly
          value="Mentor na radionicama ima ključnu ulogu u osiguravanju kvalitetnog i inspirativnog obrazovnog iskustva za sudionike. Njegova je odgovornost voditi sudionike kroz sadržaj radionice, pružati praktične smjernice te poticati interaktivnu i otvorenu razmjenu znanja. Mentor djeluje kao facilitator, osiguravajući da svi sudionici budu uključeni i motivirani za sudjelovanje, dok istovremeno prilagođava pristup individualnim potrebama grupe."
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
