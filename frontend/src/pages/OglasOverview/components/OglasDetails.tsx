import { useState } from "react";
import { Button, Modal, Typography } from "antd";
import { OglasDetailsProps } from "../typings/oglas";

const { Title, Paragraph } = Typography;

const OglasDetails = ({ title, content, author, date }: OglasDetailsProps) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <Button type="primary" onClick={showModal}>
        details
      </Button>
      <Modal open={isModalOpen} onCancel={handleCancel} footer={null}>
        <Typography>
          <Title level={3}>{title}</Title>
          <hr style={{ opacity: 0.5 }} />
          <Paragraph>{content.join("\n")}</Paragraph>
          <Paragraph>
            <strong>Author:</strong> {author}
          </Paragraph>{" "}
          <Paragraph>
            <strong>Date:</strong> {date.toLocaleDateString()}
          </Paragraph>{" "}
        </Typography>
      </Modal>
    </>
  );
};

export default OglasDetails;
