import { useState } from "react";
import { Button, Flex } from "antd";
import AddOglasModal from "./AddOglas";
import { Oglas } from "../typings/oglas";

const OglasiHeader = ({
  onAddOglas,
}: {
  onAddOglas: (oglas: Oglas) => void;
}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleAddClick = () => {
    setIsModalOpen(true);
  };

  const handleModalOk = (newOglas: Oglas) => {
    onAddOglas(newOglas); // Pass new oglas to parent
    setIsModalOpen(false);
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
        Add Oglas
      </Button>

      <AddOglasModal
        visible={isModalOpen}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
      />
    </Flex>
  );
};

export default OglasiHeader;
