import { useState } from "react";
import { Button, Flex } from "antd";
import AddOglasModal from "./AddOglas";
import { PlusIcon } from "@heroicons/react/24/solid";

const OglasiHeader = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleAddClick = () => {
    setIsModalOpen(true);
  };

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="flex justify-between">
      <h1 className="text-2xl font-medium">Oglasi</h1>
      <Button type="primary" onClick={handleAddClick}>
        Dodaj oglas <PlusIcon className="w-5 h-5" />
      </Button>

      <AddOglasModal visible={isModalOpen} onCancel={handleModalCancel} />
    </div>
  );
};

export default OglasiHeader;
