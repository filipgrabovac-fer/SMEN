import { useState } from "react";
import { Button, Flex } from "antd";
import AddOglasModal from "./AddOglas";

const OglasiHeader = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleAddClick = () => {
    setIsModalOpen(true);
  };

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <Flex
      style={{
        alignSelf: "flex-start",
        fontSize: "24px",
        fontWeight: "bold",
        lineHeight: "100px",
        justifyContent: "space-between",
        alignItems: "center",
        width: "100%",
      }}
    >
      <div>Oglasi</div>
      <Button type="primary" onClick={handleAddClick}>
        Dodaj oglas
      </Button>

      <AddOglasModal visible={isModalOpen} onCancel={handleModalCancel} />
    </Flex>
  );
};

export default OglasiHeader;
