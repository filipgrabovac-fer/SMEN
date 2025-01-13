import PrijavaModal from "./components/PrijavaModal";

const PrijavaZaMentorstvoLayout = ({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) => {
  return <PrijavaModal open={open} onClose={onClose} />;
};

export default PrijavaZaMentorstvoLayout;
