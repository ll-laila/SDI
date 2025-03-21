export interface IDeployementType {
  id?: number;
  type?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class DeployementType implements IDeployementType {
  constructor(
    public id?: number,
    public type?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
