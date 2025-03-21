export interface IClientEventType {
  id?: number;
  type?: string;
  description?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class ClientEventType implements IClientEventType {
  constructor(
    public id?: number,
    public type?: string,
    public description?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
