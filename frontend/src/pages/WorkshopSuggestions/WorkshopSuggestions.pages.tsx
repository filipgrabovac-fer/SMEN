import { Button, Table } from "antd";
import { CheckIcon, TrashIcon } from "@heroicons/react/24/solid";
import { WorkshopDetailsModal } from "./components/WorkshopDetailsModal.component";
import { useState } from "react";

export const WorkshopSuggestions = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedWorkshopId, setSelectedWorkshopId] = useState<
    number | undefined
  >(undefined);

  const onDescAndDetailsClick = () => {
    setSelectedWorkshopId(1);
    setIsModalOpen(true);
  };

  const dataSource = [
    {
      key: "1",
      tema: "Ime prve teme",
      ime_radionice: "Ime prve radionice",
      datum_prijave: "22-23-2020",
      detalji: (
        <Button
          className="text-button_border border-button_border"
          onClick={() => onDescAndDetailsClick()}
        >
          Opis i detalji
        </Button>
      ),
      approve_reject: (
        <div className="flex gap-x-2">
          <Button className="bg-button_border !hover:background-button_border">
            <CheckIcon className="w-5 h-5 text-white" />
          </Button>
          <Button>
            <TrashIcon className="w-5 h-5" color="red" />
          </Button>
        </div>
      ),
    },
    {
      key: "2",
      tema: "Ime prve teme",
      ime_radionice: "Ime prve radionice",
      datum_prijave: "22-23-2020",
      detalji: (
        <Button
          className="text-button_border border-button_border"
          onClick={() => onDescAndDetailsClick()}
        >
          Opis i detalji
        </Button>
      ),
      approve_reject: (
        <div className="flex gap-x-2">
          <Button className="bg-button_border !hover:background-button_border">
            <CheckIcon className="w-5 h-5 text-white" />
          </Button>
          <Button>
            <TrashIcon className="w-5 h-5" color="red" />
          </Button>
        </div>
      ),
    },
    {
      key: "3",
      tema: "Ime prve teme",
      ime_radionice: "Ime prve radionice",
      datum_prijave: "22-23-2020",
      detalji: (
        <Button
          className="text-button_border border-button_border"
          onClick={() => onDescAndDetailsClick()}
        >
          Opis i detalji
        </Button>
      ),
      approve_reject: (
        <div className="flex gap-x-2">
          <Button className="bg-button_border !hover:background-button_border">
            <CheckIcon className="w-5 h-5 text-white" />
          </Button>
          <Button>
            <TrashIcon className="w-5 h-5" color="red" />
          </Button>
        </div>
      ),
    },
    {
      key: "4",
      tema: "Ime prve teme",
      ime_radionice: "Ime prve radionice",
      datum_prijave: "22-23-2020",
      detalji: (
        <Button
          className="text-button_border border-button_border"
          onClick={() => onDescAndDetailsClick()}
        >
          Opis i detalji
        </Button>
      ),
      approve_reject: (
        <div className="flex gap-x-2">
          <Button className="bg-button_border !hover:background-button_border">
            <CheckIcon className="w-5 h-5 text-white" />
          </Button>
          <Button>
            <TrashIcon className="w-5 h-5" color="red" />
          </Button>
        </div>
      ),
    },
  ];

  const columns = [
    {
      title: "tema",
      dataIndex: "tema",
      key: "tema",
    },
    {
      title: "ime radionice",
      dataIndex: "ime_radionice",
      key: "ime_radionice",
    },
    {
      title: "datum prijave",
      dataIndex: "datum_prijave",
      key: "datum_prijave",
    },
    {
      title: "detalji",
      dataIndex: "detalji",
      key: "detalji",
    },
    {
      title: "",
      dataIndex: "approve_reject",
      key: "approve_reject",
    },
  ];

  return (
    <div>
      <div className="w-4/5 flex flex-col justify-center m-auto gap-y-6 mt-6">
        <p className="text-2xl font-medium">Radionice</p>
        <Table
          columns={columns}
          dataSource={dataSource}
          className="w-full m-auto"
        />
      </div>
      {selectedWorkshopId && (
        <WorkshopDetailsModal
          isModalOpen={isModalOpen}
          setIsModalOpen={setIsModalOpen}
          selectedWorkshopId={selectedWorkshopId}
          // @ts-expect-error ignore error
          setSelectedWorkshopId={setSelectedWorkshopId}
        />
      )}
    </div>
  );
};
