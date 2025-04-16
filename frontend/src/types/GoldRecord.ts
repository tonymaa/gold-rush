export interface GoldRecord {
    id?: number;
    weight: number;
    totalPrice: number;
    purchaseChannel?: string;
    remarks?: string;
    purchaseDate: string;
    photoUrl?: string;
    isSummary?: boolean;
    createTime?: string;
} 